package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HealthDetailsActivity extends AppCompatActivity {


    ImageView image;
    TextView txt,desc;

    String desc_froot[]={
            "Fungal infection is one of the most common infection, which can result in lots itching and sleepless nights. It can spread from one person to another but with proper knowledge and understanding, spread can be prevented. Though it can occur in any season or age group but is most common in summers and rainy season. Most commonly it is seen in the areas of skin folds with moisture, humidity and sweating like - below the breast, underarms, thighs, private parts. It has a tendency to come back again and again even after the treatment. But with proper and adequate treatment and few precautions it can, not only be completely cured but can be prevented as well. \n" +
            "\n" +
            "The key to successful treatment depends on how early you start the treatment because the earlier you start the treatment...... the better and the faster will be the results.....\n" +
            "\n" +
            "Below are a few tips to identify it and to prevent it.\n" +
            "\n" +
            "Symptoms\n" +
            "\n" +
            "Itching and burning\n" +
            "Formation of circular rings\n" +
            "Formation of fissures\n" +
            "Blackening of nails\n" +
            "Hair fall and flakes in the scalp\n" +
            "DO's\n" +
            "\n" +
            "Wash your clothes daily in hot water, sundry and iron them before wearing\n" +
            "Wear loose cotton clothes\n" +
            "Keep your nails well-trimmed and clean\n" +
            "DON'T\n" +
            "\n" +
            "Avoid wearing tight and wet clothes\n" +
            "Avoid manicures and pedicures especially in the rainy season\n" +
            "Avoid sharing towels, napkins or clothes\n" +
            "Avoid self-treatment\n" +
            "Avoid using any steroid creams  ",

            "Here are some preventive measures to give your kids a fighting chance against malaria.\n" +
                    "\n" +
                    "Make sure you dress your child in full sleeves and light coloured clothes. Dark-hued clothes tend to attract mosquitoes.\n" +
                    "Keep your surroundings clean. Don't let water stagnate in your garden or in buckets. These are breeding grounds for mosquitoes.\n" +
                    "Protect your child with mosquito repellents. You can use herbal sprays and body creams, which help in keeping mosquitoes away.\n" +
                    "As malaria carrying mosquitoes usually attack at night, use a mosquito bed net to protect your child from being bitten. Long lasting insecticidal nets are very useful, especially in high prevalence area. \n",




            "Trusting on home remedies to cure common cold and cough is something that many Indian households still believe. Besides treating common cold and cough effectively, these home remedies are also free from any side-effects. Here we have mentioned some of these home remedies that will help you treat common cold and cough.\n" +
                    "Ginger tea\n" +
                    "Ginger tea not only tastes good but also helps in treating common cold and cough. The tea helps in drying running and dripping nose, thus expelling phlegm from the respiratory tract. Among various health benefits of ginger, it is known to soothe common cold and speeds up the recovery process.\n" +
                    "Mixture of lemon, cinnamon and honey\n" +
                    "Another effective home remedy for common cold and cough is a mixture of lemon, cinnamon and honey. This syrup effectively cures cold and cough.\n",






            "The leaves, stems, or seeds of herbs can be used fresh, or they can be dried for later use. Dried herbs can be pounded to a fine powder, placed in airtight containers, and then stored.\n" +
                    "\n" +
                    "Some herbs are used in cooking to flavor foods. Others give scents to perfumes. Still others are used for medicines. Some herbs, such as balm and sage, are valued for their leaves. Saffron is picked for its buds and flowers. Fennel seeds are valuable in relishes and seasoning. Vanilla fruit pods yield vanilla flavoring. Ginseng is valued for its aromatic roots.",



            "Good oral hygiene and regular visits to the dentist will help you maintain healthy teeth and gums.\n" +
                    "\n" +
                    "Here are some tips to help you look after your teeth.\n" +
                    "\n" +
                    "Brush at least twice a day. The best time to brush teeth is after meals. Choose a toothbrush with a small head for better access to back teeth. Soft bristles are kinder on your gums.\n" +
                    "\n" +
                    "Use fluoridated toothpaste. Fluoride helps to harden tooth enamel and reduces your risk of decay.\n" +
                    "\n" +
                    "Brush thoroughly. Tooth brushing should take between two and three minutes.\n" +
                    "\n" +
                    "Floss your teeth daily. Use a slow and gentle sawing motion.\n" +
                    "\n" +
                    "Limit acidic drinks like soft drinks, cordials and fruit juices. Food acids soften tooth material and dissolve the minerals in tooth enamel, causing holes (cavities or caries). In severe cases, teeth may be ‘eaten’ right down to the gum.\n" +
                    "\n" +
                    "Limit sugary foods. Bacteria in dental plaque change sugars into acids.\n" +
                    "\n" +
                    "Protect your teeth from injury. Wear a mouthguard or full-face helmet when playing sports.\n" +
                    "\n" +
                    "Try to save a knocked out tooth. If possible, hold the tooth back in place while you seek immediate dental advice. If this is not possible, wrap the tooth in plastic or place it in milk and seek dental advice immediately.\n" +
                    "\n" +
                    "Avoid using your teeth for anything other than chewing food. If you use them to crack nuts, remove bottle tops or rip open packaging, you risk chipping or even breaking your teeth.\n" +
                    "\n" +
                    "See your dentist for regular check-ups. You should also visit your dentist if you have a dental problem such as a toothache or bleeding gums.",


            "Healthful meals and snacks should form the foundation of the human diet. A simple way to create a meal plan is to make sure that each meal consists of 50 percent fruit and vegetables, 25 percent whole grains, and 25 percent protein. Total fiber intake should be 25–30 grams (g) daily.\n" +
                    "\n" +
                    "Eliminate trans fats from the diet, and minimize the intake of saturated fats, which has a strong link with the incidence of coronary heart disease.\n" +
                    "\n" +
                    "Instead, people can consume monounsaturated fatty acids (MUFA) or polyunsaturated fatty acids (PUFA), which are types of unsaturated fat.\n" +
                    "\n" +
                    "The following foods are healthful and often rich in nutrients:\n" +
                    "\n" +
                    "fresh fruits and vegetables\n" +
                    "fish\n" +
                    "legumes\n" +
                    "nuts\n" +
                    "seeds\n" +
                    "whole grains, such as brown rice and oatmeal\n" +
                    "Foods to avoid eating include:\n" +
                    "\n" +
                    "foods with added oils, butter, and sugar\n" +
                    "fatty red or processed meats\n" +
                    "baked goods\n" +
                    "bagels\n" +
                    "white bread\n" +
                    "processed foods\n" +
                    "In some cases, removing certain foods from the diet might cause a person to become deficient in some necessary vitamins and minerals. A nutritionist, dietitian, or another healthcare professional can advise a person how to get enough nutrients while they are following a weight loss program.",



            "Women love beauty tips and are always on the lookout for get-gorgeous tricks that will make your hair shinier and skin flawless. We have the round up of some of the best beauty tricks every woman must know\n" +
                    "1) Use green tea for beauty\n" +
                    "Besides being a miraculous detox drink, green tea can be miraculous for your skin too. Green tea bags help in reducing swelling and stiffening the skin. When placed over closed eyes, the cooled tea bags can act wonders in getting rid of those disappointing dark circles. Try it and you will know.\n" +
                    "2) Sweet almond oil to remove lipstick\n" +
                    "A long lasting lipstick is a bliss for a long working day, but the simple idea of removing it, hassles us. Take this simple tip for getting rid of it by simply drizzling some sweet almond oil over a cotton ball and dabbing it over your lips. Super cheap when compared to those expensive make up removers, this tip is a win-win for sure.\n" +
                    "3) Buy non-condogenic products\n" +
                    "There are a lot of things associated with teenage, which makes us nostalgic, acne is certainly not amongst that. Those tiny spots on your face are very disappointing and demoralizes you. Hiding and concealing them by makeup is the only recourse left. This temporary coping strategy can be disastrous in the long run. While buying makeup, especially when you have an oily skin, make sure you use a product which is non-condogenic. Non-condogenic products are less likely to irritate the skin or clog your pores. These products won’t cause blocked pores and prevent you from those irritating acne.\n" +
                    "4) Vaseline for better eyebrow shaping\n" +
                    "Eyebrows can sometimes behave awkwardly. You can tame your unruly eyebrows by just applying some Vaseline over it and then using your eyebrow brush for further shaping. And you are done!\n" +
                    "5) Coconut hair massage before washing\n" +
                    "Hair is one of the most appealing and at the same time experimented parts of a woman’s body. Curls, straightening and hair colour, our hair handles it all. With all that damage we cause them, nourishment of your hair becomes of paramount importance. A gentle massage with some coconut oil ten minutes prior to shampooing can add wonders to your hair. Make your hair enviable and lustrous by this simple effort. ",



            " 25 Expert Fitness Tips and Strategies Every Lifter Should Know\n" +
                    "Make sure you're eating healthy. Ask almost any personal trainer and they'll tell you that regardless of your training goals, healthy eating is the backbone. ...\n" +
                    "Prepare ahead. ...\n" +
                    "Eat more clean food. ...\n" +
                    "Control your portion sizes. ...\n" +
                    "Eat with purpose.",


            "\n" +

                    "The art of practicing yoga helps in controlling an individual's mind, body and soul. It brings together physical and mental disciplines to achieve a peaceful body and mind; it helps manage stress and anxiety and keeps you relaxing. It also helps in increasing flexibility, muscle strength and body tone.",


            " \n" +
                    "\n" +
                    "Healthy aging involves good habits like eating healthy, avoiding common medication mistakes, managing health conditions, getting recommended screenings, and being active.  FDA has tips to help you age fabulously. \n" +
                    "\n" +
                    "1. Eat a Healthy Diet\n" +
                    "Good nutrition and food safety are especially important for older adults. As you age, you may be more susceptible to foodborne illness and food poisoning. You also need to make sure you eat a healthy, balanced diet. Follow these tips to help you make wise food choices and practice safe food handling.\n" +
                    "\n" +
                    "Food Safety Tips for Older Adults\n" +
                    "\n" +
                    "Sodium in Your Diet\n" +
                    "\n" +
                    "Using the Nutrition Facts Label\n" +
                    "\n" +
                    "Food Serving Sizes: Get a Reality Check\n" +
                    "\n" +
                    "2. Avoid Common Medication Mistakes\n" +
                    "Medicines can treat health problems and help you live a long, healthy life. When used incorrectly, medicines can also cause serious health problems. Use these resources to help you make smart choices about the prescription and over-the-counter medicines you take.\n" +
                    "\n" +
                    "4 Tips to Help You Use Medicines Wisely (En Español)\n" +
                    "\n" +
                    "My Medicines - A Recordkeeper to help you keep track of your medicines (En Español)\n" +
                    "\n" +
                    "Medicines and You: A Guide for Older Adults\n" +
                    "\n" +
                    "Some Medicines and Driving Don't Mix\n" +
                    "\n" +
                    "Information about Specific Drugs\n" +
                    "\n" +
                    "Tablet Splitting\n" +
                    "\n" +
                    "Buying Medicines Online\n" +
                    "\n" +
                    "3. Manage Health Conditions\n" +
                    "It is important that you work with your healthcare provider to manage health conditions like diabetes, osteoporosis, and high blood pressure. Learn more about the medicines and devices used to treat these common health problems.\n" +
                    "\n" +
                    "Heart Health for Women (En Español)\n" +
                    "\n" +
                    "High Blood Pressure (En Español)\n" +
                    "\n" +
                    "Women and Diabetes\n" +
                    "\n" +
                    "Women and Depression (En Español)\n" +
                    "\n" +
                    "Menopause\n" +
                    "\n" +
                    "Osteoporosis (En Español)\n" +
                    "\n" +
                    "Coping with Memory Loss\n" +
                    "\n" +
                    "Hearing Aids\n" +
                    "\n" +
                    "Things to Consider when Using a Medical Device in Your Home.",





            "Ayurveda offers extensive therapies and remedies for the young, old, sick, healthy, and everyone in between.\n" +
                    "\n" +
                    "This 5,000 year old medicine system that originated in India has many life-altering benefits. Here are some of the primary benefits that one can achieve with consistent use of Ayurveda.\n" +
                    "\n" +
                    "1. Weight Loss and Maintenance\n" +
                    "A healthy diet and modification in lifestyle habits through Ayurvedic treatments help shed excess body fat. In Ayurveda, weight is not a major concern but eating habits are. By allowing the body to detox through correct dietary restrictions, it is possible to achieve a toned body.\n" +
                    "\n" +
                    "Consult a practitioner to determine the best diet that will suit your nutritional needs and work with your dosha type.\n" +
                    " \n" +
                    "\n" +
                    "2. Healthy and Glowing Skin and Hair\n" +
                    "Need a perfect glow and shiny hair? Ayurveda claims that you can ditch the expensive clinical treatments and go for the organic and natural ways to achieve a glow without spending too much money. A balanced meal, toning exercises, and Ayurvedic supplements are enough to promote a healthy skin and scalp.\n" +
                    "\n" +
                    "General dietary guidelines in Ayurveda focus on consumption of fresh food taking into account your dosha type, medical history, regional produce, customs, and traditions.\n" +
                    "\n" +
                    "The focus is more on high-antioxidant foods, herbs, teas, vegetables, protein, and healthy fats.\n" +
                    " \n" +
                    "\n" +
                    "3. Say Goodbye to Stress\n" +
                    "With a fast-paced lifestyle that leaves no scope for rejuvenation or relaxation, Ayurveda guarantees reduction in stress and anxiety. Regular practice of yoga, meditation, breathing exercises, massages, and herbal treatments allow the body to calm down, detoxify, and rejuvenate.\n" +
                    "\n" +
                    "Yoga improves the autonomic nervous system making your mind alert, so you can focus well and stay energized throughout the day.\n" +
                    "\n" +
                    "Breathing exercises keep infections at bay and allow abundant oxygen supply to the cells to create a sense of awareness. Depression and anxiety can be treated with Shirodhara, Abhyangam, Shiroabhyangam, and Padabhyangam.\n" +
                    " \n" +
                    "\n" +
                    "4. Reduce Inflammation\n" +
                    "Lack of proper diet, unhealthy eating routines, insufficient sleep, irregular sleep patterns, and bad digestion can lead to inflammation. The root cause of neurological diseases, cancer, diabetes, cardiovascular problems, pulmonary diseases, arthritis, and many others starts with inflammation.\n" +
                    "\n" +
                    "As you begin to eat according to your dosha type, the digestive system begins to strengthen. Consumption of certain foods at the right times reduces toxins in the blood and digestive tract. A consequent result of this is increased vitality, high energy, and an overall decrease in lethargy and mood swings.\n" +
                    "\n" +
                    "Ayurvedic treatments are greatly known for cancer prevention as well. The best example of an herbal Ayurvedic formulation would be a combination of turmeric with black pepper.\n" +
                    " \n" +
                    "\n" +
                    "5. Cleanse the Body\n" +
                    "Panchakarma in Ayurveda is the practice of eliminating bodily toxins through enemas, oil massages, blood letting, purgation, and other oral administration.\n" +
                    "\n" +
                    "By administering these components in the human body, this allows to the elimination of food and toxins that interfere with efficient bodily functions.\n" +
                    "\n" +
                    "A cleansed body in turn aids for improved overall health. Suitable home remedies that are vastly used in Ayurvedic herbal medicines are cumin, cardamom, fennel, and ginger which cure indigestion in the body and prevent bloating.\n" +
                    "  .",





            "A healthy diet and lifestyle are the best weapons to protect against heart disease. In fact, incorporating heart-healthy foods, exercising more, maintaining a healthy weight and not smoking can help reduce cardiovascular disease-related deaths by 50 percent. With this simple 1,200-calorie meal plan, you'll protect your heart and lose a healthy 1 to 2 pounds per week in the process." +
                    "How to Meal Prep for a day " +
                    "Breakfast (271 calories)\n" +
                    "1 serving Avocado Egg Toast\n" +
                    "A.M. Snack (84 calories)\n" +
                    "1 cup blueberries\n" +
                    "Lunch (374 calories)\n" +
                    "1 serving Loaded Black Bean Nacho Soup\n" +
                    "P.M. Snack (62 calories)\n" +
                    "1 medium orange\n" +
                    "Dinner (457 calories)\n" +
                    "1 serving Seared Salmon with Green Peppercorn Sauce\n" +
                    "1 cup steamed green beans\n" +
                    "1 baked medium red potato, drizzled with 1 tsp. olive oil, 1 Tbsp. nonfat plain Greek yogurt and a pinch of pepper.\n" +
                    "Daily Totals: 1,224 calories, 60 g protein, 142 g carbohydrates, 28 g fiber, 52 g fat, 11 g sat. fat., 828 mg sodium",


            "\n" +
                    "Homeopathy is a holistic medicine which uses specially prepared, highly diluted substances (given mainly in tablet form) with the aim of triggering the body’s own healing mechanisms.\n" +
                    "\n" +
                    "A homeopath will prescribe medicines according to the patient’s specific set of symptoms, and how they experience them, taking into account their overall level of health.\n" +
                    "\n" +
                    "Like with like\n" +
                    "Homeopathy is based on the principle of “like treats like” – that is, a substance which can cause symptoms when taken in large doses, can be used in small amounts to treat similar symptoms. For example, drinking too much coffee can cause sleeplessness and agitation, so according to this principle, when made into a homeopathic medicine, it could be used to treat people suffering from sleeplessness and agitation.\n" +
                    "\n" +
                    "This concept is sometimes used in conventional medicine, for example, the stimulant Ritalin is used to treat patients with ADHD (Attention Deficit Hyperactivity Disorder), or small doses of allergens, such as pollen, are sometimes used to de-sensitise allergic patients. An important difference with homeopathy however, is that the medicinal doses (known as remedies) are so small that toxic side-effects are avoided.\n" +
                    "\n" +
                    "Its origins\n" +
                    "The principle of treating “like with like” dates back to Hippocrates (460-377BC) but in its current form, homeopathy has been widely used worldwide for more than 200 years.\n" +
                    "\n" +
                    "It was discovered by a German doctor, Samuel Hahnemann, who was looking for a way to reduce the damaging side effects associated with the medical treatment of his day, which included the use of poisons. He began experimenting on himself and a group of healthy volunteers, giving smaller and smaller medicinal doses, discovering that as well as reducing toxicity, the medicines became more effective as the doses were lowered.\n" +
                    "\n" +
                    "He also observed that symptoms caused by the toxic ‘medicines’ of the time, such as mercury which was used to treat syphilis, were similar to those of the diseases they were being used to treat, which led to the principle he described as ‘like cures like’.\n" +
                    "\n" +
                    "Hahnemann went on to document his work, and his texts formed the foundations of homeopathic medicine as it is practised today. A BBC Radio 4 documentary aired in December 2010 described Hahnemann as a medical pioneer who worked tirelessly to improve medical practice, insisting that medicines were tested before use."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_details);

        image= findViewById(R.id.img);
        txt= findViewById(R.id.txt12);
        desc=findViewById(R.id.desc);
        try {


            Intent intent = getIntent();
            String fruitname = intent.getStringExtra("v1");
            Integer img1=intent.getIntExtra("v2",0);
            Integer index=intent.getIntExtra("v3",0);
            txt.setText(fruitname);
            image.setImageResource(img1);

            desc.setText(desc_froot[index]);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
